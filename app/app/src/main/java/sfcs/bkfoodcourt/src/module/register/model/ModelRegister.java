package sfcs.bkfoodcourt.src.module.register.model;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sfcs.bkfoodcourt.src.model.BaseResponse;
import sfcs.bkfoodcourt.src.model.ErrorResponse;
import sfcs.bkfoodcourt.src.model.User;
import sfcs.bkfoodcourt.src.module.register.presenter.PresenterRegister;
import sfcs.bkfoodcourt.src.network.APIVnFood;
import sfcs.bkfoodcourt.src.network.IApiVnFood;
import sfcs.bkfoodcourt.src.utils.ErrorUtils;

public class ModelRegister {
    public void register(User user, final PresenterRegister presenterRegister) {
        IApiVnFood apiService = APIVnFood.getAPIVnFood().create(IApiVnFood.class);
        Call<BaseResponse<User>> call = apiService.handlerRegister(user.getEmail(), user.getPassword(), user.getPhone(), "", user.getUsername());
        call.enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                if (response.isSuccessful()) {
                    presenterRegister.resultRegister(true, "Đăng ký thành công");
                } else {
                    ErrorResponse err = ErrorUtils.parseError(response);
                    presenterRegister.resultRegister(false, err.getStatusCode() + " - " + err.getErr());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                presenterRegister.resultRegister(false, t.getMessage());

            }
        });
    }
}

