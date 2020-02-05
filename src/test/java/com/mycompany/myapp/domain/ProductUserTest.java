package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ProductUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductUser.class);
        ProductUser productUser1 = new ProductUser();
        productUser1.setId(1L);
        ProductUser productUser2 = new ProductUser();
        productUser2.setId(productUser1.getId());
        assertThat(productUser1).isEqualTo(productUser2);
        productUser2.setId(2L);
        assertThat(productUser1).isNotEqualTo(productUser2);
        productUser1.setId(null);
        assertThat(productUser1).isNotEqualTo(productUser2);
    }
}
