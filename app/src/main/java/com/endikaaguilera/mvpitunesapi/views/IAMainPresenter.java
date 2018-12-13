package com.endikaaguilera.mvpitunesapi.views;

import com.endikaaguilera.mvpitunesapi.models.IAData;

import static com.endikaaguilera.mvpitunesapi.global.types.RecyclerViewHolderType.*;

class IAMainPresenter {

    private IAMainView mainView;
    private final IAMainInteractor interactor;

    IAMainPresenter(IAMainView mainView, IAMainInteractor interactor) {

        this.mainView = mainView;
        this.interactor = interactor;

    }

    void onDestroy() {

        if (this.interactor != null) this.interactor.onDestroy();
        this.mainView = null;

    }

    void onDataError() {

        if (this.mainView != null) this.mainView.onShowPlaceholder(NO_DATA);

    }

    void onError() {

        if (this.mainView != null) this.mainView.onShowPlaceholder(ERROR);

    }

    void onNetworkError() {

        if (this.mainView != null) this.mainView.onShowPlaceholder(NO_INTERNET);

    }

    void getData() {

        if (this.mainView != null) this.mainView.onGetData();

    }

    void getData(IAMainInteractor.IAGetDataListener listener) {

        if (this.mainView != null) this.mainView.onShowPlaceholder(LOADING);
        if (this.interactor != null) this.interactor.getData(listener);

    }

    void showResultsRecyclerView(IAData data) {

        if (this.mainView != null) this.mainView.onShowResults(data);

    }

    void showNoNetworkMessage() {

        if (this.mainView != null) this.mainView.onNoNetworkOnItemClick();

    }

    void showBottomView() {

        if (this.mainView != null) this.mainView.onShowBottomView();

    }

    void hideBottomView() {

        if (this.mainView != null) this.mainView.onHideBottomView();

    }

}
