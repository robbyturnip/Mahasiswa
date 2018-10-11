package robbyturnip333.gmail.com.mahasiswa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import robbyturnip333.gmail.com.mahasiswa.CRUD;
import robbyturnip333.gmail.com.mahasiswa.Model.ModelData;
import robbyturnip333.gmail.com.mahasiswa.R;

/**
 * Created by robby on 18/07/18.
 */

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> {
    private List<ModelData> mItems ;
    private Context context;

    public AdapterData (Context context, List<ModelData> items)
    {
        this.mItems = items;
        this.context = context;
    }

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(HolderData holder, int position) {
        ModelData md  = mItems.get(position);
        holder.tvnama.setText(md.getNama());
        holder.tvnim.setText(md.getNim());
        holder.tvprodi.setText(md.getProdi());
        String base = md.getImage();
        byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        holder.tvphoto.setImageBitmap(bmp);
        holder.md = md;


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    class HolderData extends RecyclerView.ViewHolder
    {
        TextView tvnama,tvnim,tvprodi;
        ImageView tvphoto;
        ModelData md;

        public  HolderData (View view)
        {
            super(view);

            tvnama = (TextView) view.findViewById(R.id.nama);
            tvnim = (TextView) view.findViewById(R.id.nim);
            tvprodi = (TextView) view.findViewById(R.id.prodi);
            tvphoto = (ImageView) view.findViewById(R.id.pp);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent update = new Intent(context, CRUD.class);
                    update.putExtra("update",1);
                    update.putExtra("nim",md.getNim());
                    update.putExtra("nama",md.getNama());
                    update.putExtra("prodi",md.getProdi());
                    update.putExtra("email",md.getEmail());
                    update.putExtra("image",md.getImage());
                    context.startActivity(update);
                }
            });
        }
    }
}
