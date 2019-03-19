package artifact.modules.common.dao.impl;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;


public  class BaseElasticSearchDaoImpl<T> {



    public List<T> list(String index,String type,Map query ,String sort){




        return null;
    }


    /**
     * 获取子类实际泛型参数
     * @return
     */
    private Class getGenericClass() {
        Type type = this.getClass().getGenericSuperclass();
        type = ((ParameterizedType) type).getActualTypeArguments()[0];
        return (Class<T>) type;
    }

}
