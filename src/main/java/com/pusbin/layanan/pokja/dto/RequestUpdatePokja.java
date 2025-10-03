package com.pusbin.layanan.pokja.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestUpdatePokja {
    @NotBlank(message = "Pokja tidak boleh kosong")
    @Size(max = 100, message = "Pokja maksimal 100 karakter")
    private String namaPokja;
}
