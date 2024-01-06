package com.gdsc.cofence;

import com.gdsc.cofence.entity.workplaceManagement.WorkPlace;
import com.gdsc.cofence.repository.WorkplaceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
public class CoFenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoFenceApplication.class, args);}

    @Bean
    public CommandLineRunner initDatabase(WorkplaceRepository repository) {
        return args -> {
            List<WorkPlace> workplaces = Arrays.asList(
                    new WorkPlace(1L, "서대문구 상업용 건물 (2)", "서울 서대문구 고리울로 52번길 70-18"),
                    new WorkPlace(2L, "강남구 주거상업 복합 아파트 (3)", "서울 강남구 강남대로 1234"),
                    new WorkPlace(3L, "종로구 문화 공간 (1)", "서울 종로구 세종대로 189"),
                    new WorkPlace(4L, "송파구 건설 현장 (5)", "서울 송파구 올림픽로 99"),
                    new WorkPlace(5L, "구로구 산업 단지 (7)", "서울 구로구 디지털로 34"),
                    new WorkPlace(6L, "수원 아파트 건설 (4)", "경기 수원시 권선구 영통로 56"),
                    new WorkPlace(7L, "인천 송도 뉴타운 (8)", "인천 연수구 송도동 123-45"),
                    new WorkPlace(8L, "대전 도로 시설 건설 (6)", "대전 서구 계백로 789"),
                    new WorkPlace(9L, "광주 북구 상업 시설 (2)", "광주 북구 첨단과기로 210"),
                    new WorkPlace(10L, "울산 남구 주택 건설 (1)", "울산 남구 삼산로 432"),
                    new WorkPlace(11L, "분당 주거 단지 (5)", "경기 성남시 분당구 정자동 432-1"),
                    new WorkPlace(12L, "제주도 리조트 건설 (3)", "제주특별자치도 서귀포시 서귀포대로 567"),
                    new WorkPlace(13L, "광진구 고층 사무용 건물 (6)", "서울 광진구 강변북로 789"),
                    new WorkPlace(14L, "화성시 도시 개발 (2)", "경기 화성시 화성로 123"),
                    new WorkPlace(15L, "부산 해안 공원 건설 (8)", "부산 수영구 광안리해변로 210"),
                    new WorkPlace(16L, "세종시 정부 복합단지 (4)", "세종특별자치시 정부청사"),
                    new WorkPlace(17L, "평창 동계 스포츠 복합시설 (7)", "강원도 평창군 대관령로 789"),
                    new WorkPlace(18L, "전주 한옥마을 보존 사업 (1)", "전라북도 전주시 완산구 전주천동로 32"),
                    new WorkPlace(19L, "동두천 기술 공원 확장 (10)", "경기 동두천시 테크노로 102"),
                    new WorkPlace(20L, "김해 국제공항 터미널 리노베이션 (9)", "부산 강서구 공항로 567")
            );
            repository.saveAll(workplaces);

        };
    }
}
