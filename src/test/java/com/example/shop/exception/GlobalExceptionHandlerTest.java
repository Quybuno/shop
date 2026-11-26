package com.example.shop.exception;

import com.example.shop.Service.CategoryService;
import com.example.shop.controller.CategoryController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    CategoryService categoryService;             // service giả

    @Test
    void loiRangBuocDatabase_tra409_vaKhongLoChiTiet() throws Exception {
        // Cho: controller thật + handler thật, service giả ném lỗi database
        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(new CategoryController(categoryService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        doThrow(new DataIntegrityViolationException("fk_products_category: chi tiet noi bo"))
                .when(categoryService).delete("A");

        // Khi gọi DELETE, thì nhận 409 và không thấy tên ràng buộc
        mockMvc.perform(delete("/api/categories/A"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(content().string(not(containsString("fk_products_category"))));
    }
}
