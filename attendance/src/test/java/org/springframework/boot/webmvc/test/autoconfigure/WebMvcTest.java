package org.springframework.boot.webmvc.test.autoconfigure;

import com.timetrack.attendance.Controller.AsistenciaController;

public @interface WebMvcTest {

    Class<AsistenciaController> value();

}
