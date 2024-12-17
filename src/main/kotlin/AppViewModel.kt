import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import model.Month
import model.MonthColors
import java.io.File
import java.nio.file.Files
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timer
import kotlin.io.path.Path

class AppViewModel() {
    var currentDay          = mutableStateOf(Calendar.getInstance().get(Calendar.DAY_OF_YEAR))
        private set
    var currentMonthName    = mutableStateOf(Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG_FORMAT, Locale.UK))
        private set
    var currentMonth        = mutableStateOf(Calendar.getInstance().get(Calendar.MONTH))
        private set
    var currentMonthColor   = mutableStateOf(Color.Black)
        private set
    var currentYear         = mutableStateOf(Calendar.getInstance().get(Calendar.YEAR))
        private set

    //List containing the first letter of each weekday.
    var weekdayFirstLetterList = mutableListOf<String>()
        private set
    //list with all the organized list of the displayed month. YYYY - MM - List of Week
    var weekList = mutableListOf<Triple<Int, Int, List<Int>>>()
        private set
    //list of all the month of the year
    var monthList = mutableListOf<Month>()

    //Loading variables
    private var isLoading = false
    private var isLoadingMutex = Mutex(false)

    //Timer
    private val timerScope = CoroutineScope(Job() + Dispatchers.IO)
    private val generalMutex = Mutex()



    init {
        initializer()
        setupWeekdayArray()
        setCorrectMonthColor()
        setupWeekList()
        setupMonthList()
    }

    /**
     * go to the previous month of the calendar and update color and weekList
     */
    fun previousMonth(){

        if(currentMonth.value == 0)
            return

        var loadingState = false
        runBlocking{
            isLoadingMutex.withLock(){
                loadingState = isLoading
            }
        }

        if(isLoading)
            return

        runBlocking{
            isLoadingMutex.withLock() {
                isLoading = true
            }
        }

        CoroutineScope(Job() + Dispatchers.IO).launch{
            val calendar = Calendar.getInstance()
            val currentMonthVal = currentMonth.value
            calendar.set(Calendar.MONTH, currentMonthVal - 1)
            currentMonthName.value = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG_FORMAT, Locale.UK)
            currentMonth.value = calendar.get(Calendar.MONTH)
            setCorrectMonthColor(calendar)
            weekList.clear()
            setupWeekList(calendar)
            isLoading = false
        }
    }

    /**
     * go to the next month of the calendar and update color and weekList
     */
    fun nextMonth() {
        if(currentMonth.value == 11)
            return

        var loadingState = false
        runBlocking{
            isLoadingMutex.withLock() {
                loadingState = isLoading
            }
        }

        if(loadingState)
            return

        runBlocking{
            isLoadingMutex.withLock(){
                isLoading = true
            }
        }
        CoroutineScope(Job() + Dispatchers.IO).launch{
            val calendar = Calendar.getInstance()
            val currentMonthVal = currentMonth.value
            calendar.set(Calendar.MONTH, currentMonthVal + 1)
            currentMonthName.value = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG_FORMAT, Locale.UK)
            currentMonth.value = calendar.get(Calendar.MONTH)
            setCorrectMonthColor(calendar)
            weekList.clear()
            setupWeekList(calendar)
            isLoading = false
        }
    }



    /**
     * Private section of the ViewModel
     */


    //Init Function
    private fun initializer(){
        if(!File("./images").exists()){
            Files.createDirectory(Path("./images"))
        }

        timerScope.launch{
            val calendar = Calendar.getInstance()
            //Set the calendar to the next day at 00:00
            calendar.set(Calendar.HOUR, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            calendar.add(Calendar.HOUR_OF_DAY, 12)
            print(calendar.time)

            delay(1000)         //DO NOT DELETE
            timer(
                name = "update date timer",
                daemon = false,
                startAt = calendar.time,
                period = TimeUnit.DAYS.toMillis(1)      //Update each day
            ){
                currentDay.value = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
                currentMonthName.value = Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG_FORMAT, Locale.UK)
                currentMonth.value = Calendar.getInstance().get(Calendar.MONTH)
                currentYear.value = Calendar.getInstance().get(Calendar.YEAR)
                setupWeekdayArray()
                setCorrectMonthColor()
                setupWeekList()
                setupMonthList()
            }
        }
    }

    /**
     * set up the list with all the first letter of each weekday.
     */
    private fun setupWeekdayArray(){
        for(day in DayOfWeek.values()){
            weekdayFirstLetterList.add(day.toString().substring(0,1))
        }
    }

    /**
     * set up the color palette for the page using the color associated to the month
     */
    private fun setCorrectMonthColor(calendar: Calendar = Calendar.getInstance()){
        currentMonthColor.value = MonthColors.colors[calendar.get(Calendar.MONTH)] ?: Color.Black
    }

    /**
     * set up the week list for the displayed month. This list of list will be used to set up the days' grid.
     */
    private fun setupWeekList(calendar: Calendar = Calendar.getInstance()){
        calendar.set(Calendar.DAY_OF_MONTH, 1)                  //set the date to the first day of the current month
        val lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)  //get last day of the month
        val month = calendar.get(Calendar.MONTH)                    //referred month
        val year = calendar.get(Calendar.YEAR)                      //referred year

        var tempList = mutableListOf<Int>(0,0,0,0,0,0,0)
        while (true){
            if(calendar.get(Calendar.DAY_OF_WEEK) != 1) {
                tempList[calendar.get(Calendar.DAY_OF_WEEK) - 2] = calendar.get(Calendar.DAY_OF_MONTH)
                if(calendar.get(Calendar.DAY_OF_MONTH) == lastDay)
                    weekList.add(Triple(year, month, tempList))
            }else{
                tempList[6] = calendar.get(Calendar.DAY_OF_MONTH)
                //new week
                weekList.add(Triple(year,month,tempList))
                tempList = mutableListOf<Int>(0,0,0,0,0,0,0)
            }
            if (calendar.get(Calendar.DAY_OF_MONTH) == lastDay)
                break
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)
        }
    }

    /**
     * create the array with all the months object containing the full name, the image url and associated color.
     */
    private fun setupMonthList(){
        val months = listOf("jan", "feb", "mar", "apr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dec")
        for(x in months.indices)
        {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.MONTH, x)
            monthList.add(Month(id = x + 1, name = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG_FORMAT, Locale.UK),
                color = MonthColors.colors[x]!!, imageURL = "./images/${months[x]}.png"))
        }
    }
}