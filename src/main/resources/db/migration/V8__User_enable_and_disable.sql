ALTER TABLE `jm_clone_medium`.`users`
    ADD COLUMN `lock_status` BIT NOT NULL DEFAULT TRUE AFTER `is_activated`;