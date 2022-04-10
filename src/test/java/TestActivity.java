import com.crm.exception.LoginException;
import com.crm.utils.MD5Util;
import com.crm.utils.ServiceFactory;
import com.crm.utils.UUIDUtil;
import com.crm.workbench.domain.Activity;
import com.crm.workbench.service.ActivityService;
import com.crm.workbench.service.impl.ActivityServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class TestActivity {

    @Test
    public void Test01() throws Exception {
        /*Activity a = new Activity();
        a.setId(UUIDUtil.getUUID());
        a.setName("宣传推广会");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Assert.assertEquals(as.save(a),true);*/
        Map<String, String> pMap = new HashMap<>();
        ResourceBundle rb = ResourceBundle.getBundle("Stage2Possibility");
        Enumeration<String> e = rb.getKeys();

        while(e.hasMoreElements()){
            String key = e.nextElement();
            String value = rb.getString(key);
            pMap.put(key,value);
        }
        System.out.println(pMap);
    }


}
