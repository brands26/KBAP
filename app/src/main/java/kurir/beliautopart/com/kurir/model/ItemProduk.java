package kurir.beliautopart.com.kurir.model;

/**
 * Created by brandon on 13/05/16.
 */
public class ItemProduk {
    private int idItem;
    private String kategori;
    private String namaItem;
    private int sts;
    private int harga;
    private String kompatibel;
    private String kode;
    private String foto;
    private int jumlah;
    private boolean reject=false;


    public ItemProduk() {
    }

    public ItemProduk(int idItem, String namaItem, int sts, int harga, String kompatibel, String kode, String foto) {
        this.idItem = idItem;
        this.namaItem = namaItem;
        this.sts = sts;
        this.harga = harga;
        this.kompatibel = kompatibel;
        this.kode = kode;
        this.foto = foto;
    }

    public ItemProduk(int idItem, String namaItem, int sts, int harga, String kompatibel, String kode, String foto, int jumlah) {
        this.idItem = idItem;
        this.namaItem = namaItem;
        this.sts = sts;
        this.harga = harga;
        this.kompatibel = kompatibel;
        this.kode = kode;
        this.foto = foto;
        this.jumlah = jumlah;
    }

    public ItemProduk(int idItem, String kategori, String namaItem, int sts, int harga, String kompatibel, String kode, String foto) {
        this.idItem = idItem;
        this.kategori = kategori;
        this.namaItem = namaItem;
        this.sts = sts;
        this.harga = harga;
        this.kompatibel = kompatibel;
        this.kode = kode;
        this.foto = foto;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public String getNamaItem() {
        return namaItem;
    }

    public void setNamaItem(String namaItem) {
        this.namaItem = namaItem;
    }

    public int getSts() {
        return sts;
    }

    public void setSts(int sts) {
        this.sts = sts;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getKompatibel() {
        return kompatibel;
    }

    public void setKompatibel(String kompatibel) {
        this.kompatibel = kompatibel;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public boolean isReject() {
        return reject;
    }

    public void setReject(boolean reject) {
        this.reject = reject;
    }

    @Override
    public String toString() {
        return ""+idItem;
    }
}
