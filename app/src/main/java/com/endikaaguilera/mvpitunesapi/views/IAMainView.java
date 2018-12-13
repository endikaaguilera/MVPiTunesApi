package com.endikaaguilera.mvpitunesapi.views;

import com.endikaaguilera.mvpitunesapi.global.types.RecyclerViewHolderType;
import com.endikaaguilera.mvpitunesapi.models.IAData;

interface IAMainView {

    void onGetData();

    void onNoNetworkOnItemClick();

    void onShowResults(IAData data);

    void onShowPlaceholder(@RecyclerViewHolderType.RVHolderType int type);

    void onShowBottomView();

    void onHideBottomView();

}
