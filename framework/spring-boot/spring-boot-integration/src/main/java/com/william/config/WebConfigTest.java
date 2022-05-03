package com.william.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by william.zhang on 2016/3/18.
 */
@Component
@Profile({"online"})
public class WebConfigTest {
}
