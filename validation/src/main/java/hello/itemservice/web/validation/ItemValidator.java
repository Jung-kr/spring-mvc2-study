package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
        // 파라미터로 넘어온 clazz가 Item에 지원이 되냐 (==써도 되지만 자식 클래스까지 커버하기 위해 isAssignableFrom 사용)
        // item == class | item == subItem
    }

    @Override
    public void validate(Object target, Errors errors) {    // Error파라미터로 BindingResult를 받을 수 있음
        Item item = (Item) target;

        //BindingResult는 이미 본인이 검증해야 할 객체인 target 을 알고 있음
        // => rejectValue() , reject()를 사용해서 FieldError , ObjectError를 직접 생성하지 않고 기존 코드를 단순화

        //검증 로직
        if (!StringUtils.hasText(item.getItemName())) { //if문 하나는 ValidationUtils를 사용하면 한줄로 가능
            errors.rejectValue("itemName", "required");
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }
        if (item.getQuantity() == null || item.getQuantity() > 9999) {
            errors.rejectValue("quantity", "max", new Object[]{"9999"}, null);
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

    }
}
