import java.text.SimpleDateFormat
import java.util.Date

object DateUtil {

  val pattern = "MM/dd/yyyy HH:mm:ss"
  val df = new SimpleDateFormat(pattern)

  def fromStringToDate(date: String): Date = {
    df.parse(date)
  }

  def fromDateToString(date: Date): String = {
    df.format(date)
  }

}

