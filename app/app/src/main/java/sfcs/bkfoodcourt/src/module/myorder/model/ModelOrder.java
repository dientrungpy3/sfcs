package sfcs.bkfoodcourt.src.module.myorder.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sfcs.bkfoodcourt.src.model.BaseResponse;
import sfcs.bkfoodcourt.src.model.ErrorResponse;
import sfcs.bkfoodcourt.src.model.Gift;
import sfcs.bkfoodcourt.src.model.Order;
import sfcs.bkfoodcourt.src.model.OrderDetails;
import sfcs.bkfoodcourt.src.module.myorder.presenter.PresenterOrder;
import sfcs.bkfoodcourt.src.network.APIVnFood;
import sfcs.bkfoodcourt.src.network.IApiVnFood;
import sfcs.bkfoodcourt.src.utils.ErrorUtils;

public class ModelOrder {
    IApiVnFood apiService = APIVnFood.getAPIVnFood().create(IApiVnFood.class);

    public void addToCart(Order order, List<OrderDetails> orderDetails, PresenterOrder presenterOrder) {
        Gson gson = new GsonBuilder().create();

        Call<BaseResponse<String>> callOrder = apiService.addOrder(gson.toJson(orderDetails), gson.toJson(order));

        callOrder.enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                if (response.isSuccessful()) {
                    presenterOrder.resultAddCart(true, response.body().getData());
                } else {
                    ErrorResponse err = ErrorUtils.parseError(response);
                    presenterOrder.resultAddCart(false, err.getErr());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                presenterOrder.resultAddCart(false, t.getMessage());

            }
        });
    }

    public void checkGift(String codeGift, PresenterOrder presenterOrder) {
        Call<BaseResponse<Gift>> callGift = apiService.checkGift(codeGift);
        callGift.enqueue(new Callback<BaseResponse<Gift>>() {
            @Override
            public void onResponse(Call<BaseResponse<Gift>> call, Response<BaseResponse<Gift>> response) {
                if (response.isSuccessful()) {
                    presenterOrder.resultCheckGift(true, response.body().getData(), "");
                } else {
                    ErrorResponse err = ErrorUtils.parseError(response);
                    presenterOrder.resultCheckGift(false, null, err.getErr());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Gift>> call, Throwable t) {
                presenterOrder.resultCheckGift(false, null, t.getMessage());

            }
        });
    }
}
