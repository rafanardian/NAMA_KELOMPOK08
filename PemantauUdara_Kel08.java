import java.util.Scanner;
 
public class PemantauUdara_Kel08 {
 
	// ===== FUNCTION (static) =====
 
	// Non-return type, tanpa parameter
	static void cetakHeader() {
        System.out.println("==================================================");
    	System.out.println("  SISTEM PEMANTAU KUALITAS UDARA - Kelompok 08");
        System.out.println("==================================================");
    }
 
	// Non-return type, berparameter
	static void cetakGaris(int panjang) {
    	for (int i = 0; i < panjang; i++) {
        	System.out.print("-");
    	}
    	System.out.println();
	}
 
	// Return type, tanpa parameter
	static int ambilBatasAman() {
    	return 100;
	}
 
	// Return type, berparameter
	static String tentukanKategori(int aqi) {
    	if (aqi <= 50) {
        	return "Baik";
    	} else if (aqi <= 100) {
        	return "Sedang";
    	} else if (aqi <= 150) {
        	return "Tidak Sehat bagi Kelompok Sensitif";
    	} else if (aqi <= 200) {
        	return "Tidak Sehat";
    	} else if (aqi <= 300) {
	        return "Sangat Tidak Sehat";
    	} else {
        	return "Berbahaya";
    	}
	}
 
	public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);
    	final int JUMLAH_DATA = 5;
    	cetakHeader();
 
    	// Validasi jumlah stasiun (do-while)
    	int jumlahStasiun;
    	do {
        	System.out.print("Jumlah stasiun pemantau (1-3): ");
        	jumlahStasiun = sc.nextInt();
            if (jumlahStasiun < 1 || jumlahStasiun > 3) {
                System.out.println("Jumlah stasiun tidak valid, ulangi!");
        	}
    	} while (jumlahStasiun < 1 || jumlahStasiun > 3);
 
    	StasiunUdara[] daftar = new StasiunUdara[jumlahStasiun];
 
    	// Input data tiap stasiun (for bersarang dengan while untuk validasi)
    	for (int s = 0; s < jumlahStasiun; s++) {
        	System.out.print("\nNama stasiun ke-" + (s + 1) + ": ");
        	String nama = sc.next();
  	      daftar[s] = new StasiunUdara(nama);
 
        	for (int k = 1; k <= JUMLAH_DATA; k++) {
            	System.out.print("  Indeks udara (AQI) pembacaan ke-" + k + ": ");
            	int nilai = sc.nextInt();
            	while (nilai < 0 || nilai > 500) {
                    System.out.print("  Nilai harus 0-500, ulangi: ");
                	nilai = sc.nextInt();
            	}
                daftar[s].catatPembacaan(nilai);
        	}
    	}
 
    	// Laporan akhir
    	System.out.println();
    	cetakHeader();
    	int batas = ambilBatasAman();
    	int terburuk = 0;
    	for (int s = 0; s < jumlahStasiun; s++) {
        	daftar[s].tampilkanRiwayat();
        	double rata = daftar[s].hitungRataRata();
        	int melebihi = daftar[s].hitungMelebihi(batas);
        	System.out.printf("Rata-rata AQI : %.1f%n", rata);
        	System.out.println("Kategori  	: " + tentukanKategori((int) rata));
            System.out.println("Pembacaan > " + batas + " : " + melebihi + " kali");
        	if (melebihi >= 3) {
                System.out.println("PERINGATAN: stasiun ini sering melewati batas aman!");
        	}
        	cetakGaris(50);
        	if (rata > daftar[terburuk].hitungRataRata()) {
            	terburuk = s;
        	}
    	}
    	System.out.println("Stasiun dengan kualitas udara terburuk: " + daftar[terburuk].getNama());
    	sc.close();
	}
}
 
// ===== METHOD (di dalam class) =====
class StasiunUdara {
	private String nama;
	private int[] data = new int[5];
    private int terisi = 0;
 
	StasiunUdara(String nama) {
    	this.nama = nama;
	}
 
	// Non-return type, berparameter
	void catatPembacaan(int aqi) {
    	if (terisi < data.length) {
        	data[terisi] = aqi;
        	terisi++;
    	}
	}
 
	// Non-return type, tanpa parameter
	void tampilkanRiwayat() {
    	System.out.println("Stasiun " + nama + " (Kel08)");
    	System.out.print("Riwayat AQI   : ");
    	for (int i = 0; i < terisi; i++) {
        	System.out.print(data[i] + (i < terisi - 1 ? ", " : ""));
    	}
    	System.out.println();
	}
 
	// Return type, tanpa parameter
	double hitungRataRata() {
    	int total = 0;
  	  for (int i = 0; i < terisi; i++) {
        	total += data[i];
    	}
    	return (double) total / terisi;
	}
 
	// Return type, berparameter
	int hitungMelebihi(int batas) {
    	int hitung = 0;
    	for (int i = 0; i < terisi; i++) {
        	if (data[i] > batas) {
            	hitung++;
        	}
    	}
    	return hitung;
	}
 
	String getNama() {
    	return nama;
	}
}
