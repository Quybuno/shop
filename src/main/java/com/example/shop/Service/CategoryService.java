package com.example.shop.Service;


import com.example.shop.DTO.request.CategoriesRequest;
import com.example.shop.DTO.response.CategoryResponse;
import com.example.shop.DTO.response.PageResponse;
import com.example.shop.Entity.Categories;
import com.example.shop.exception.AppException;
import com.example.shop.exception.ErrorCode;
import com.example.shop.mapper.CategoryMapper;
import com.example.shop.repository.CategoriesRepository;
import com.example.shop.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoriesRepository categoriesRepository;
    private final ProductsRepository productsRepository;
    private final CategoryMapper categoryMapper;

//    public List<Categories> getCategories(){
//        return categoriesRepository.findAll();
//    }

//    public List<CategoryResponse> getCategoryResponse(){
//        List<CategoryResponse> responses =
//    }

    public CategoryResponse getCategoriesById(String id){
        Categories categories = categoriesRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

//        return CategoryResponse.builder()
//                .id(categories.getId())
//                .name(categories.getName())
//                .description(categories.getDecription())
//                .createdAt(categories.getCreated_at())
//                .updatedAt(categories.getUpdate_at())
//                .build();
//
        return categoryMapper.toCategoryResponse(categories);
    }

    @Transactional(readOnly = true)
    public PageResponse<CategoryResponse> getCategories(Pageable pageable) {
        Page<Categories> categoryPage = categoriesRepository.findAll(pageable);

        Page<CategoryResponse> responsePage = categoryPage.map(categoryMapper::toCategoryResponse);

        return PageResponse.from(responsePage);
    }

    public CategoryResponse createCategories(CategoriesRequest request){
        if(categoriesRepository.existsByNameIgnoreCase(request.getName().trim())){
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
//        Categories categories = new Categories();
//        categories.setName(request.getName().trim());
//        categories.setDecription(request.getDescription());
//        return CategoryResponse.from(categoriesRepository.save(categories));
        Categories categories = new Categories();
        categories.setName(request.getName());
        categories.setDecription(request.getDescription());
        return categoryMapper.toCategoryResponse(categoriesRepository.save(categories));


    }

    public CategoryResponse updateCategories(String id,CategoriesRequest request){
        Categories categories = categoriesRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        if(categoriesRepository.existsByNameIgnoreCaseAndIdNot(request.getName().trim() , id)){
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        categories.setName(request.getName().trim());
        if(request.getDescription() == null || request.getDescription().isBlank()) {
            categories.setDecription(categories.getDecription());
        }
        else{
            categories.setDecription(request.getDescription().trim());
        }

//        categories.setDecription(request.getDescription());
//        return CategoryResponse.from(categoriesRepository.save(categories));
        return categoryMapper.toCategoryResponse(categoriesRepository.save(categories));
    }




    public void delete(String id){
        Categories categories = categoriesRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        if(productsRepository.existsByCategoriesId(id)){
            throw new AppException(ErrorCode.CATEGORY_IN_USE);
        }
        categoriesRepository.deleteById(id);

    }



}
