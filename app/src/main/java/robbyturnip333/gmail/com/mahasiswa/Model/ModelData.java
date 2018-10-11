package robbyturnip333.gmail.com.mahasiswa.Model;

/**
 * Created by robby on 18/07/18.
 */

public class ModelData {
    String nim, nama, prodi, email, image;

    public ModelData(){}

    public ModelData(String nim, String nama, String prodi, String email, String image) {
        this.nim = nim;
        this.nama = nama;
        this.prodi = prodi;
        this.email = email;
        this.image = image;

    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim= nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
