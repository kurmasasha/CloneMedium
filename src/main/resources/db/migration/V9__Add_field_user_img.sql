ALTER TABLE `jm_clone_medium`.`users`
    ADD COLUMN `img` VARCHAR(255) NULL DEFAULT 'no-img.png' AFTER `role_id`;