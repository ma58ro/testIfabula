package com.example.testo.utility;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Response {
    private String status;
    private Object data;
    private Map<String, Object> meta = new HashMap<>();

    public Map toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("status", status);
        map.put("meta", meta);

        return map;
    }

    public void setData(Object data) {
        if (data instanceof Page) {
            Page pageData = (Page) data;
            this.data = pageData.getContent();
            meta.put("totalPage", pageData.getTotalPages());
            meta.put("totalCount", pageData.getTotalElements());
            meta.put("currentPage", pageData.getPageable().getPageNumber() + 1);
            meta.put("currentPageSize", pageData.getPageable().getPageSize());
        } else if (data instanceof Iterable) {
            this.data = data;
        } else {
            LinkedList<Object> list = new LinkedList<>();
            list.add(data);
            this.data = list;
        }
    }

    public static ResponseEntity create(Object data, HttpStatus httpStatus) {
        Response output = new Response();
        switch (httpStatus) {
            case OK:
            case ACCEPTED:
            case NO_CONTENT:
            case CREATED:
                output.setStatus("success");
                break;
            case BAD_REQUEST:
            case FORBIDDEN:
            case METHOD_NOT_ALLOWED:
            case NOT_FOUND:
            case UNAUTHORIZED:
            default:
                output.setStatus("error");
        }
        output.setData(data);

        return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON).body(output);
    }

    public static ResponseEntity create(Object data, int status) {
        return create(data, HttpStatus.valueOf(status));
    }
    public static ResponseEntity notFound(Object data) {
        return create(data, HttpStatus.NOT_FOUND);
    }
    public static ResponseEntity badRequest(Object data) {
        return create(data, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity ok(Object data) {
        return create(data, HttpStatus.OK);
    }

}
