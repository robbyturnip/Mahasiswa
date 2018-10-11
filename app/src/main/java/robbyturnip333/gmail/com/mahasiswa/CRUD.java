package robbyturnip333.gmail.com.mahasiswa;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import robbyturnip333.gmail.com.mahasiswa.Util.AppController;
import robbyturnip333.gmail.com.mahasiswa.Util.ServerAPI;

public class CRUD extends AppCompatActivity {
    private static final int PICK_IMAGE_ID = 234;
    EditText nim,nama,prodi,email,nim_backup,nama_backup,prodi_backup, email_backup;
    Button btn_del_batal,btn_simpan_update;
    ProgressDialog pd;
    ImageView imageView;
    String intent_image,image_perubahan, btn1,btn2 ;
    int update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);


        Intent data = getIntent();
        update = data.getIntExtra("update",0);
        String intent_nim = data.getStringExtra("nim");
        String intent_nama = data.getStringExtra("nama");
        String intent_prodi = data.getStringExtra("prodi");
        String intent_email = data.getStringExtra("email");
        intent_image = data.getStringExtra("image");
        String intent_nim_backup = data.getStringExtra("nim");
        String intent_nama_backup = data.getStringExtra("nama");
        String intent_prodi_backup = data.getStringExtra("prodi");
        String intent_email_backup = data.getStringExtra("email");


        imageView=findViewById(R.id.foto);
        imageView.setClickable(true);
        nim = (EditText) findViewById(R.id.inp_nim);
        nama = (EditText) findViewById(R.id.inp_nama);
        prodi = (EditText) findViewById(R.id.inp_prodi);
        email = (EditText) findViewById(R.id.inp_email);
        nim_backup = (EditText) findViewById(R.id.inp_nim_backup);
        nama_backup  = (EditText) findViewById(R.id.inp_nama_backup);
        prodi_backup  = (EditText) findViewById(R.id.inp_prodi_backup);
        email_backup  = (EditText) findViewById(R.id.inp_email_backup);
        btn_del_batal = (Button) findViewById(R.id.btn_cancel);
        btn_simpan_update = (Button) findViewById(R.id.btn_simpan);
        pd = new ProgressDialog(CRUD.this);

        /*kondisi update / insert*/
        if(update == 1)
        {
            String base = intent_image;
            byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
            imageView.setImageBitmap(bmp);
            btn_simpan_update.setText("Update");
            btn_del_batal.setText("Delete");
            nim.setText(intent_nim);
            nama.setText(intent_nama);
            prodi.setText(intent_prodi);
            email.setText(intent_email);
            nim_backup.setText(intent_nim_backup);
            nama_backup.setText(intent_nama_backup);
            prodi_backup.setText(intent_prodi_backup);
            email_backup.setText(intent_email_backup);
            nim_backup.setVisibility(View.GONE);
            nama_backup.setVisibility(View.GONE);
            prodi_backup.setVisibility(View.GONE);
            email_backup.setVisibility(View.GONE);
            btn1=btn_simpan_update.getText().toString();
            btn2=btn_del_batal.getText().toString();

        }
        else if(update > 1)
        {
            String base = image_perubahan;
            byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
            imageView.setImageBitmap(bmp);
            btn_simpan_update.setText("Update");
            btn_del_batal.setText("Delete");
            nim.setText(intent_nim);
            nama.setText(intent_nama);
            prodi.setText(intent_prodi);
            email.setText(intent_email);
            nim_backup.setText(intent_nim_backup);
            nama_backup.setText(intent_nama_backup);
            prodi_backup.setText(intent_prodi_backup);
            email_backup.setText(intent_email_backup);
            nim_backup.setVisibility(View.GONE);
            nama_backup.setVisibility(View.GONE);
            prodi_backup.setVisibility(View.GONE);
            email_backup.setVisibility(View.GONE);

        }
        else {
            Drawable gambar= getResources().getDrawable(R.drawable.image);
            Bitmap bit= ((BitmapDrawable)gambar).getBitmap();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bit.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] data1 = bos.toByteArray();
            String file = Base64.encodeToString(data1, 0);
            this.image_perubahan = file;
            nim_backup.setVisibility(View.GONE);
            nama_backup.setVisibility(View.GONE);
            prodi_backup.setVisibility(View.GONE);
            email_backup.setVisibility(View.GONE);
        }



        btn_simpan_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn1=="Update")
                {
                    Update_data();

                }else {
                    simpanData();
                }
            }
        });

        btn_del_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn2 == "Delete")
                {
                    deleteData();
                }else {
                    Intent main = new Intent(CRUD.this,MainActivity.class);
                    startActivity(main);
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseImageIntent = ImagePicker.getPickImageIntent(CRUD.this);
                startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
            }

        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
                if (requestCode == PICK_IMAGE_ID && resultCode == -1) {
                    Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                    bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, false);
                    imageView.setImageBitmap(bitmap);
                    btn1=btn_simpan_update.getText().toString();
                    btn2=btn_del_batal.getText().toString();
                    image_perubahan=bitmap.toString();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    byte[] data1 = bos.toByteArray();
                    String file = Base64.encodeToString(data1, 0);
                    this.image_perubahan = file;
                    this.intent_image=image_perubahan;
                    update=2;
                }
                else {
                    Toast.makeText(CRUD.this, "Anda Tidak Memilih Gambar ", Toast.LENGTH_SHORT).show();
                }

        }

    private void Update_data()
    {
        pd.setMessage("Update Data ");
        pd.setCancelable(false);
        pd.show();


        StringRequest updateReq = new StringRequest(Request.Method.POST, ServerAPI.URL_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(CRUD.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        startActivity( new Intent(CRUD.this,MainActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Toast.makeText(CRUD.this, "pesan :  Gagal Update Data ", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("nim",nim.getText().toString());
                map.put("nama",nama.getText().toString());
                map.put("prodi",prodi.getText().toString());
                map.put("email",email.getText().toString());
                map.put("image",intent_image);
                map.put("nimbackup",nim_backup.getText().toString());
                map.put("namabackup",nama_backup.getText().toString());
                map.put("prodibackup",prodi_backup.getText().toString());
                map.put("emailbackup",email_backup.getText().toString());
                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(updateReq);
    }

    private void deleteData()
    {
        pd.setMessage("Delete Data ...");
        pd.setCancelable(false);
        pd.show();

        StringRequest delReq = new StringRequest(Request.Method.POST, ServerAPI.URL_DELETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        Log.d("tampil","response : " + response.toString());
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(CRUD.this,"pesan : " +res.getString("message"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        startActivity(new Intent(CRUD.this,MainActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Log.d("tampil", "error : " + error.getMessage());
                        Toast.makeText(CRUD.this, "pesan : gagal menghapus data ", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("nim",nim.getText().toString());
                map.put("nama",nama.getText().toString());
                map.put("prodi",prodi.getText().toString());
                map.put("email",email.getText().toString());
                map.put("image",intent_image);
                map.put("nimbackup",nim_backup.getText().toString());
                map.put("namabackup",nama_backup.getText().toString());
                map.put("prodibackup",prodi_backup.getText().toString());
                map.put("emailbackup",email_backup.getText().toString());
                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(delReq);
    }



    private void simpanData()
    {

        pd.setMessage("Menyimpan Data");
        pd.setCancelable(false);
        pd.show();


        StringRequest sendData = new StringRequest(Request.Method.POST, ServerAPI.URL_INSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(CRUD.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        startActivity( new Intent(CRUD.this,MainActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Toast.makeText(CRUD.this, "pesan : Gagal Tambah Data ", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("nim",nim.getText().toString());
                map.put("nama",nama.getText().toString());
                map.put("prodi",prodi.getText().toString());
                map.put("email",email.getText().toString());
                map.put("image",image_perubahan);
                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(sendData);

    }
}

