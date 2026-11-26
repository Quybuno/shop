package com.example.shop.Service;

import com.example.shop.DTO.request.CategoriesRequest;
import com.example.shop.DTO.response.CategoryResponse;
import com.example.shop.Entity.Categories;
import com.example.shop.exception.AppException;
import com.example.shop.exception.ErrorCode;
import com.example.shop.mapper.CategoryMapper;
import com.example.shop.repository.CategoriesRepository;
import com.example.shop.repository.ProductsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    CategoriesRepository categoriesRepository;   // repository giả

    @Mock
    ProductsRepository productsRepository;

    @Spy
    CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);   // mapper thật

    @InjectMocks
    CategoryService categoryService;             // service thật, nhận repository giả và mapper

    @Test
    void update_idKhongTonTai_nemNotFound() {
        // Cho
        when(categoriesRepository.findById("X")).thenReturn(Optional.empty());
        CategoriesRequest request = new CategoriesRequest("Laptop", null);

        // Khi
        AppException ex = assertThrows(AppException.class,
                () -> categoryService.updateCategories("X", request));

        // Thì
        assertEquals(ErrorCode.CATEGORY_NOT_FOUND, ex.getErrorCode());
        verify(categoriesRepository, never()).save(any());
    }

    @Test
    void update_tenTrungDanhMucKhac_nemExisted() {
        // Cho
        Categories phone = danhMuc("B", "Phone", "cu");
        when(categoriesRepository.findById("B")).thenReturn(Optional.of(phone));
        when(categoriesRepository.existsByNameIgnoreCaseAndIdNot("Laptop", "B")).thenReturn(true);
        CategoriesRequest request = new CategoriesRequest("Laptop", null);

        // Khi
        AppException ex = assertThrows(AppException.class,
                () -> categoryService.updateCategories("B", request));

        // Thì
        assertEquals(ErrorCode.CATEGORY_EXISTED, ex.getErrorCode());
        verify(categoriesRepository, never()).save(any());
    }

    @Test
    void update_chiGuiTen_doiTenVaGiuMoTa() {
        // Cho
        Categories laptop = danhMuc("A", "Laptop", "Accc");
        when(categoriesRepository.findById("A")).thenReturn(Optional.of(laptop));
        when(categoriesRepository.save(any(Categories.class))).thenAnswer(i -> i.getArgument(0));
        CategoriesRequest request = new CategoriesRequest("Tablet", null);

        // Khi
        CategoryResponse response = categoryService.updateCategories("A", request);

        // Thì
        assertEquals("Tablet", response.getName());
        assertEquals("Accc", response.getDescription());
        verify(categoriesRepository).save(laptop);
    }

    @Test
    void update_moTaToanDauCach_giuMoTaCu() {
        // Cho
        Categories laptop = danhMuc("A", "Laptop", "Accc");
        when(categoriesRepository.findById("A")).thenReturn(Optional.of(laptop));
        when(categoriesRepository.save(any(Categories.class))).thenAnswer(i -> i.getArgument(0));
        CategoriesRequest request = new CategoriesRequest("Tablet", "   ");

        // Khi
        CategoryResponse response = categoryService.updateCategories("A", request);

        // Thì
        assertEquals("Tablet", response.getName());
        assertEquals("Accc", response.getDescription());
    }

    @Test
    void update_coDauCachThua_trimTenVaMoTa() {
        // Cho
        Categories laptop = danhMuc("A", "Laptop", "Accc");
        when(categoriesRepository.findById("A")).thenReturn(Optional.of(laptop));
        when(categoriesRepository.save(any(Categories.class))).thenAnswer(i -> i.getArgument(0));
        CategoriesRequest request = new CategoriesRequest("  Tablet  ", "  16GB  ");

        // Khi
        CategoryResponse response = categoryService.updateCategories("A", request);

        // Thì
        assertEquals("Tablet", response.getName());
        assertEquals("16GB", response.getDescription());
    }

    @Test
    void delete_idKhongTonTai_nemNotFound() {
        // Cho
        when(categoriesRepository.findById("X")).thenReturn(Optional.empty());

        // Khi
        AppException ex = assertThrows(AppException.class,
                () -> categoryService.delete("X"));

        // Thì
        assertEquals(ErrorCode.CATEGORY_NOT_FOUND, ex.getErrorCode());
        verify(categoriesRepository, never()).deleteById(any());
    }

    @Test
    void delete_conSanPham_nemInUse() {
        // Cho
        Categories laptop = danhMuc("A", "Laptop", "Accc");
        when(categoriesRepository.findById("A")).thenReturn(Optional.of(laptop));
        when(productsRepository.existsByCategoriesId("A")).thenReturn(true);

        // Khi
        AppException ex = assertThrows(AppException.class,
                () -> categoryService.delete("A"));

        // Thì
        assertEquals(ErrorCode.CATEGORY_IN_USE, ex.getErrorCode());
        verify(categoriesRepository, never()).deleteById(any());
    }

    @Test
    void delete_khongConSanPham_xoaDuoc() {
        // Cho
        Categories laptop = danhMuc("A", "Laptop", "Accc");
        when(categoriesRepository.findById("A")).thenReturn(Optional.of(laptop));
        when(productsRepository.existsByCategoriesId("A")).thenReturn(false);

        // Khi
        categoryService.delete("A");

        // Thì
        verify(categoriesRepository).deleteById("A");
    }

    private Categories danhMuc(String id, String name, String description) {
        Categories categories = new Categories();
        categories.setId(id);
        categories.setName(name);
        categories.setDecription(description);
        return categories;
    }
}
