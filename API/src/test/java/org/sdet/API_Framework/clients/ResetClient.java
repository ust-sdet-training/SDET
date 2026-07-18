package org.sdet.API_Framework.clients;

import io.restassured.response.Response;
import org.sdet.API_Framework.base.BaseAPI;
import org.sdet.API_Framework.constants.Endpoints;

public class ResetClient extends BaseAPI {

    public Response resetData() {

        return post(
                Endpoints.RESET
        );

    }

}