package com.hubspot.singularity.helpers;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RFC5545ScheduleTest {

  @Test
  public void testEveryWeekdayRFC5545Schdule() throws Exception {
    String schedule = "FREQ=DAILY;BYDAY=MO,TU,WE,TH,FR";
    Date nextValidTime = new RFC5545Schedule(schedule).getNextValidTime();
    Assertions.assertTrue(nextValidTime.after(new Date()));
    Assertions.assertTrue(
      nextValidTime.before(
        new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(3))
      )
    );
  }

  @Test
  public void testMaxIterations() throws Exception {
    String schedule = "DTSTART=19970902T090000\nRRULE:FREQ=HOURLY;COUNT=10001";
    Assertions.assertEquals(
      new RFC5545Schedule(schedule).getStartDateTime(),
      new org.joda.time.DateTime(1997, 9, 2, 9, 0, 0)
    );
    Assertions.assertEquals(new RFC5545Schedule(schedule).getNextValidTime(), null);
  }

  @Test
  public void testRecurInPastDoesNotGiveNext() throws Exception {
    String schedule =
      "FREQ=YEARLY;INTERVAL=4;BYMONTH=11;BYDAY=TU;BYMONTHDAY=2,3,4,5,6,7,8;COUNT=1";
    Assertions.assertEquals(new RFC5545Schedule(schedule).getNextValidTime(), null);
  }
}
