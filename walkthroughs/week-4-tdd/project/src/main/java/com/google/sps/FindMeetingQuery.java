// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public final class FindMeetingQuery {
    //Event gives info about: title, TimeRange, Attendees
    //Request gives info about: duration, attendees
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
      //want to return a Collection of TimeRange -available for meeting 
      //look at event, meeting request, and timerange
      // use the TimeRange function fromStartEnd
      //foreach all event in collection to get all time ranges
    Collection<TimeRange> TimeOrder = new ArrayList<>();
    //timeRange has start and end variable
    for (Event event : events) {
        //add all time ranges to a list 
        TimeOrder.add(event.getWhen());
    }
    //sorting TimeOrder list 
    Collection.sort(TimerOrder, (Event a, Event b) ->
    a.getWhen().compareTo(b.getWhen()));
    System.out.print(TimeOrder);
      // when get list of ordered Timerange
      //then need to go comparing them to see if they overlap
      // and check duration of meeting 
      // then need to see if ppl overlap 
      
      
     return TimeOrder;  
  }
}
