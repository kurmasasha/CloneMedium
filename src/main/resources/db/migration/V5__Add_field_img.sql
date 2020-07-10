ALTER TABLE `jm_clone_medium`.`topics`
    ADD COLUMN `img` VARCHAR(255) NULL DEFAULT 'no-img.png' AFTER `title`;