package cn.rzpt.model.result;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataResult<T> {

    private Integer code;

    private T data;

    private String message;

    public static <T> DataResult<T> success(T data) {
        DataResult<T> objectDataResult = new DataResult<>();
        objectDataResult.data = data;
        objectDataResult.code = DataResultCodeEnum.SUCCESS.getCode();
        objectDataResult.message = DataResultCodeEnum.SUCCESS.getMessage();
        return objectDataResult;
    }

    public static <T> DataResult<T> fail() {
        DataResult<T> objectDataResult = new DataResult<>();
        objectDataResult.data = null;
        objectDataResult.code = DataResultCodeEnum.FAIL.getCode();
        objectDataResult.message = DataResultCodeEnum.FAIL.getMessage();
        return objectDataResult;
    }


    public static <T> DataResult<T> fail(Integer code,String message) {
        DataResult<T> objectDataResult = new DataResult<>();
        objectDataResult.data = null;
        objectDataResult.code = code;
        objectDataResult.message = message;
        return objectDataResult;
    }

    public static <T> DataResult<T> fail(DataResultCodeEnum dataResultCodeEnum) {
        DataResult<T> objectDataResult = new DataResult<>();
        objectDataResult.data = null;
        objectDataResult.code = dataResultCodeEnum.getCode();
        objectDataResult.message = dataResultCodeEnum.getMessage();
        return objectDataResult;
    }

}
