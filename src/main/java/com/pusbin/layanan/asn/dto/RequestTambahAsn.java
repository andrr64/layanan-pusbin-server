package  com.pusbin.layanan.asn.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestTambahAsn {
    @NotBlank(message = "Nama ASN Tidak Boleh kosong")
    @Size(max = 100, message = "Nama ASN maksimal 100 karakter")
    private String jenis_asn;
}