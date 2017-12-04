package com.gmail.at.connorglennon.weekendassignment3.dagger2;

import com.gmail.at.connorglennon.weekendassignment3.view.search.SearchFragment;

import dagger.Component;

/**
 * Created by Connor Glennon on 04/12/2017.
 */

@Component(modules = {PresenterModule.class})
public interface SearchPresenterComponent {
    void inject(SearchFragment searchFragment);
}
