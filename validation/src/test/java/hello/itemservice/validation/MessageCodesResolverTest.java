package hello.itemservice.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.*;

public class MessageCodesResolverTest {

    // rejectValue()와 reject()는 내부에서 MessageCodesResolver를 사용
    // BindingResult에 MessageCodesResolver를 통해 생성된 오류 코드를 보관
    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    // ObjectError -> reject()
    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        assertThat(messageCodes).containsExactly("required.item", "required");  // 2가지의 오류 코드 생성
    }

    // FieldError -> rejectValue()
    @Test
    void messageCodesResolverField() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        assertThat(messageCodes).containsExactly(   // 4가지의 오류 코드 생성
                "required.item.itemName",
                "required.itemName",
                "required.java.lang.String",
                "required"
        );
    }
}
