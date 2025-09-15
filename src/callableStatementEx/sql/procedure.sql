-- 샘플테이블 생성
create table CODE1 (
     CID int, cName VARCHAR(50)
);
desc code1;

insert into CODE1(cid,cname)
select ifnull(max(cid),0)+1 as cld2,'TEST' as cName2
from CODE1;

select * from code1;

truncate code1;

-- 프로시져 생성 :  P_INSERTCODES()

delimiter $$
create procedure P_INSERTCODES(IN cData VARCHAR(255),
                               IN cTname VARCHAR(255),
                               OUT resultMsg VARCHAR(255))
begin
    set @strsql = concat(
                  'INSERT INTO ',cTname,'(cid,cname)',
                  'SELECT COALESCE(MAX(cid),0)+1,? FROM ', cTname
                  );
    -- 바인딩할 변수 설정
    set @cData = cData;
    set resultMsg = 'Insert Sucess!';

    prepare stmt from @strsql;
    execute stmt USING @cData;

    DEALLOCATE PREPARE stmt;
    commit;
end $$
delimiter ;

call P_INSERTCODES('프론트디자이너','CODE1',@result);
call P_INSERTCODES('백엔드개발자','CODE1',@result);
select @result;
select * from code1;




-- 연습
CREATE TABLE TB_MEMBER (
                           m_seq INT AUTO_INCREMENT PRIMARY KEY,  -- 자동 증가 시퀀스
                           m_userid VARCHAR(20) NOT NULL,
                           m_pwd VARCHAR(20) NOT NULL,
                           m_email VARCHAR(50) NULL,
                           m_hp VARCHAR(20) NULL,
                           m_registdate DATETIME DEFAULT NOW(),  -- 기본값: 현재 날짜와 시간
                           m_point INT DEFAULT 0
);

-- 반드시  중복 사용자 예외 처리  (이미 존재하는 사용자 검사 시행)
-- 만약 성공적이면  숫자 200 리턴 , 이미 가입된 회원이라면 숫자 100 리턴

-- 프로시저 작성 SP_MEMBER_INSERT()
delimiter $$
CREATE PROCEDURE SP_MEMBER_INSERT(
    IN V_USERID VARCHAR(20),
    IN V_PWD VARCHAR(20),
    IN V_EMAIL VARCHAR(50),
    IN V_HP VARCHAR(20),
    OUT RTN_CODE INT
)
BEGIN
    DECLARE v_count int;

    SELECT COUNT(m_seq) into v_count FROM TB_MEMBER WHERE M_USERID = V_USERID;

    IF v_count > 0 THEN
        SET RTN_CODE = 100;
    ELSE
        INSERT INTO TB_MEMBER (M_USERID, M_pwd, M_EMAIL,M_HP)
        VALUES (V_USERID,V_PWD,V_EMAIL,V_HP);
        SET RTN_CODE = 200;
    END IF;
    COMMIT;
end  $$
delimiter ;

CALL SP_MEMBER_INSERT('apple','1111','apple@sample.com','010-9898-9999',@result);
SELECT @result;

SELECT * FROM TB_MEMBER;
show create procedure SP_MEMBER_INSERT;


-- SP_MEMBER_LIST() 프로시저를 생성   :  전체 회원들의 정보를 출력하는 기능입니다.
delimiter $$
create procedure SP_MEMBER_LIST()
begin
    select * from TB_MEMBER;
    commit;
end $$
delimiter ;

call SP_MEMBER_LIST();


-- 회원 m_userid 로 회원 정보 확인
delimiter $$
create procedure SP_MEMBER_ONE(IN V_userid varchar(20))
begin
    select * from TB_MEMBER WHERE m_userid = V_userid;
    commit;
end $$
delimiter ;

call SP_MEMBER_ONE('apple');