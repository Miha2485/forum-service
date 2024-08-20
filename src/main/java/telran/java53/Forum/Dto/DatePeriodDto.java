package telran.java53.Forum.Dto;


import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DatePeriodDto {
	LocalDate dateFrom;
    LocalDate dateTo;
}