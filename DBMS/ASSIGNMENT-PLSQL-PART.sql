
-- 3. CHECK PRIME OR NOT 
DELIMITER $$

CREATE PROCEDURE check_prime(IN n INT)
BEGIN
    DECLARE i INT DEFAULT 2;
    DECLARE flag INT DEFAULT 1;

    IF n <= 1 THEN
        SELECT CONCAT(n, ' is NOT prime') AS result;
    ELSE
        prime_loop: WHILE i <= n/2 DO
            IF MOD(n, i) = 0 THEN
                SET flag = 0;
                LEAVE prime_loop;
            END IF;
            SET i = i + 1;
        END WHILE prime_loop;

        IF flag = 1 THEN
            SELECT CONCAT(n, ' is PRIME') AS result;
        ELSE
            SELECT CONCAT(n, ' is NOT PRIME') AS result;
        END IF;
    END IF;
END$$

DELIMITER ;


-- 4. STAR PATTERN
DELIMITER $$

CREATE PROCEDURE star_pattern()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE str VARCHAR(20);

    WHILE i <= 4 DO
        SET str = REPEAT('* ', i);
        SELECT str;
        SET i = i + 1;
    END WHILE;

END$$

DELIMITER ;


-- 5. Update BOOK Cost With Conditions (MySQL)
DELIMITER $$

CREATE PROCEDURE update_book_cost(IN bno INT, IN new_cost INT)
BEGIN
    DECLARE old_cost INT;

    SELECT COST INTO old_cost
    FROM BOOKS_COPY
    WHERE BOOK_NO = bno;

    IF old_cost < 450 AND new_cost < 900 THEN
        UPDATE BOOKS_COPY
        SET COST = new_cost
        WHERE BOOK_NO = bno;

        SELECT 'Cost updated successfully' AS message;
    ELSE
        SELECT 'ERROR: Condition not satisfied' AS message;
    END IF;
END$$

DELIMITER ;

-- 6. Display Words of a String in Separate Lines
DELIMITER $$

CREATE PROCEDURE split_words(IN sentence VARCHAR(200))
BEGIN
    DECLARE pos INT;
    DECLARE word VARCHAR(50);

    WHILE LENGTH(sentence) > 0 DO
        SET pos = LOCATE(' ', sentence);

        IF pos = 0 THEN
            SET word = sentence;
            SET sentence = '';
        ELSE
            SET word = SUBSTRING(sentence, 1, pos - 1);
            SET sentence = SUBSTRING(sentence, pos + 1);
        END IF;

        SELECT word;
    END WHILE;
END$$

DELIMITER ;


-- FOR PRIME CHECK
CALL CHECK_PRIME(17);

-- FOR STAR PATTERN
CALL star_pattern();

-- FOR UPDATE BOOK_COPY
CALL update_book_cost(1, 600);

-- FOR SEPARATE LINE
CALL split_words('Hi im MG');

