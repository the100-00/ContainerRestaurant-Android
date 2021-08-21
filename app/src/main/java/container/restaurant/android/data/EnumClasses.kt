package container.restaurant.android.data

import androidx.lifecycle.MutableLiveData

enum class Category(val menuKorean: String) {
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


enum class SortingCategory(val title: String, var selected: MutableLiveData<Boolean>, val sort: String) {
    LATEST("최신순", MutableLiveData(true), "createdDate"),
    LIKE("좋아요 많은 순", MutableLiveData(false), "likeCount"),
    EASY("난이도 낮은 순", MutableLiveData(false), "difficulty"),
    //TODO 미완성 부분 고치기
    HARD("난이도 높은 순", MutableLiveData(false), "d")
    ;
}