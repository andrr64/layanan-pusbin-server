package com.pusbin.layanan.pokja;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pusbin.layanan.pokja.dto.RequestTambahPokja;
import com.pusbin.layanan.pokja.dto.RequestUpdatePokja;
import com.pusbin.common.dto.ApiResponse;
import com.pusbin.common.dto.GetFilter;


@RestController
@RequestMapping("api/v1/pokja")
public class PokjaController {
    private final PokjaService service;

    public PokjaController(PokjaService service) {
        this.service = service;
    }

    // GET /pokja
    @GetMapping
    public List<Pokja> getAll() {
        return service.getAllPokja();
    }

    // GET /pokja/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Pokja> getById(@PathVariable Long id) {
        return service.getPokjaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /pokja
    @PostMapping
    public Pokja create(@RequestBody RequestTambahPokja body) {
        return service.createPokja(body.getNamaPokja());
    }

    // PUT /pokja/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Pokja> update(@PathVariable Long id, @RequestBody RequestUpdatePokja body) {
        try {
            Pokja updated = service.updatePokja(id, body.getNamaPokja());
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /pokja/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deletePokja(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filters")
    public ApiResponse<GetFilter> getFilter() {
        try {
            return ApiResponse.success("OK", service.getFilters());
        } catch (Exception e) {
            return ApiResponse.fail(e.toString());
        }
    }
}
