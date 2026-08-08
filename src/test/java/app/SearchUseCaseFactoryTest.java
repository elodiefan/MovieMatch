package app;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import interface_adapter.ViewManagerModel;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchViewModel;
import interface_adapter.search_result.SearchResultViewModel;
import views.SearchResultView;
import views.SearchView;

class SearchUseCaseFactoryTest {

    @Test
    void createWiresTheSameControllerIntoBothViews() throws Exception {
        final ViewManagerModel viewManagerModel = new ViewManagerModel();
        final SearchViewModel searchViewModel = new SearchViewModel();
        final SearchResultViewModel resultViewModel = new SearchResultViewModel();
        final SearchView searchView = new SearchView(
                searchViewModel, viewManagerModel, "home");
        final SearchResultView resultView = new SearchResultView(
                resultViewModel, viewManagerModel, SearchViewModel.VIEW_NAME);

        SearchUseCaseFactory.create(viewManagerModel, searchViewModel,
                resultViewModel, searchView, resultView);

        final SearchController searchController = controllerFrom(searchView);
        assertNotNull(searchController);
        assertSame(searchController, controllerFrom(resultView));
    }

    private static SearchController controllerFrom(Object view) throws Exception {
        final Field field = view.getClass().getDeclaredField("searchController");
        field.setAccessible(true);
        return (SearchController) field.get(view);
    }
}
