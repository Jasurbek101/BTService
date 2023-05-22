package uz.BTService.btservice.entity.base;

import java.io.Serializable;
import java.util.Objects;

public abstract class BaseObject {
    public abstract Integer getUniqueId();

    @Override
    public int hashCode() {
        return Objects.hashCode(getUniqueId());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (!getClass().getName().equalsIgnoreCase(obj.getClass().getName())) {
            return false;
        } else if (this == obj) {
            return true;
        } else return obj instanceof BaseObject && Objects.equals(getUniqueId(), ((BaseObject) obj).getUniqueId());
    }

    @Override
    public String toString() {
        return getClass().getName() + "{" +
                "id=" + getUniqueId() +
                '}';
    }
}
