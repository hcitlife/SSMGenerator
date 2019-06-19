package ${pkg};

import ${serviceNameWithPkg};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ${clazzName}Controller {

	@Autowired
	private ${clazzName}Service ${clazzName?uncap_first}Service ;



}
