package ir.piana.dev.core.api.dto;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 6/16/2019 5:49 PM
 **/
public class ErrorDto implements Serializable {
    private String code;
    private String description;
    private Map<String, Object> params = new HashMap<>();

    public ErrorDto() {
    }

    public ErrorDto(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public ErrorDto(String code, String description, Map<String, Object> params) {
        this.code = code;
        this.description = description;
        this.params.putAll(params);
    }

    public ErrorDto(String code, Map<String, Object> params) {
        this.code = code;
        this.params.putAll(params);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorDto errorDto = (ErrorDto) o;
        return Objects.equal(code, errorDto.code) &&
                Objects.equal(description, errorDto.description) &&
                Objects.equal(params, errorDto.params);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code, description, params);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ErrorDto{");
        sb.append("code='").append(code).append('\'');
        sb.append(", message='").append(description).append('\'');
        sb.append(", params=").append(params);
        sb.append('}');
        return sb.toString();
    }
}
