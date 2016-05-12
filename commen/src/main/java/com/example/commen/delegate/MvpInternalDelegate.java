/*
 * Copyright 2015 Hannes Dorfmann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.commen.delegate;


import com.example.commen.ui.MvpPresenter;
import com.example.commen.ui.activity.BaseView;

/**
 * This is just the internal implementation for the delegate. Don't use it by your own.
 *
 * @param <V> The type of {@link MvpView}
 * @param <P> The type of {@link MvpPresenter}
 * @author Hannes Dorfmann
 * @since 1.1.0
 */
class MvpInternalDelegate<V extends BaseView, P extends MvpPresenter<V>> {

  protected BaseMvpDelegateCallback<V, P> delegateCallback;

  MvpInternalDelegate(BaseMvpDelegateCallback<V, P> delegateCallback) {

    if (delegateCallback == null) {
      throw new NullPointerException("MvpDelegateCallback is null!");
    }

    this.delegateCallback = delegateCallback;
  }

  /**
   * Called  to create the presenter (if no other one already exisits)
   */
  void createPresenter() {

    P presenter = delegateCallback.getPresenter();
    if (presenter == null) {
      presenter = delegateCallback.createPresenter();
    }
    if (presenter == null) {
      throw new NullPointerException("Presenter is null! Do you return null in createPresenter()?");
    }

    delegateCallback.setPresenter(presenter);
  }

  /**
   * Attaches the view to the presenter
   */
  void attachView() {
    getPresenter().attachView(delegateCallback.getMvpView());
  } 

  /**
   * Called to detach the view from presenter
   */
  void detachView() {
    getPresenter().detachView(delegateCallback.shouldInstanceBeRetained());
  }

  private P getPresenter() {
    P presenter = delegateCallback.getPresenter();
    if (presenter == null) {
      throw new NullPointerException("Presenter returned from getPresenter() is null");
    }
    return presenter;
  }
}
