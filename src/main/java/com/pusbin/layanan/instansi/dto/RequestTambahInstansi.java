package  com.pusbin.layanan.instansi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestTambahInstansi {
    @NotBlank(message = "Nama Instansi Tidak Boleh kosong")
    @Size(max = 100, message = "Nama Instansi maksimal 100 karakter")
    private String nama_instansi;
    private Long id_kategori_instansi;
    private Long id_jenis_instansi;
    private Long id_wilayah_kerja;
    private Long id_pokja;
}