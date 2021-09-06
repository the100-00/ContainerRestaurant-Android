package container.restaurant.android.data

import androidx.lifecycle.MutableLiveData

enum class FoodCategory(val menuKorean: String) {
    KOREAN("한식"),
    NIGHT_MEAL("야식"),
    CHINESE("중식"),
    SCHOOL_FOOD("분식"),
    FAST_FOOD("패스트푸드"),
    ASIAN_AND_WESTERN("아시안/양식"),
    COFFEE_AND_DESSERT("카페/디저트"),
    JAPANESE("돈까스/회/일식"),
    CHICKEN_AND_PIZZA("치킨/피자")
}


enum class SortingCategory(val title: String, var isItemSelected: MutableLiveData<Boolean>, val sort: String) {
    LATEST("최신순", MutableLiveData(true), "createdDate"),
    LIKE("좋아요 많은 순", MutableLiveData(false), "likeCount"),
    EASY("난이도 낮은 순", MutableLiveData(false), "difficulty"),
    HARD("난이도 높은 순", MutableLiveData(false), "difficulty")
    ;
}

internal enum class FeedCategory(val menuKorean: String, var isItemSelected: MutableLiveData<Boolean>) {

    ALL("전체", MutableLiveData(true)),
    KOREAN("한식", MutableLiveData(false)),
    SCHOOL_FOOD("분식", MutableLiveData(false)),
    FAST_FOOD("패스트푸드", MutableLiveData(false)),
    JAPANESE("돈까스/회/일식", MutableLiveData(false)),
    ASIAN_AND_WESTERN("아시안/양식", MutableLiveData(false)),
    CHINESE("중식", MutableLiveData(false)),
    NIGHT_MEAL("야식", MutableLiveData(false)),
    COFFEE_AND_DESSERT("카페/디저트", MutableLiveData(false)),
    CHICKEN_AND_PIZZA("치킨/피자", MutableLiveData(false)),
    ;
}