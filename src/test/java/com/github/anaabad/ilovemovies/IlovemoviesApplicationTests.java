package com.github.anaabad.ilovemovies;

import com.github.anaabad.ilovemovies.common.DbTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@DbTest
@ActiveProfiles("test")
class IlovemoviesApplicationTests {

	@Test
	void contextLoads() {
	}

}
