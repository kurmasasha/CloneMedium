package ru.javamentor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.javamentor.model.Notification;

/**hashtags
 * Класс представляющий объект DTO нотификации
 *
 * @version 1.0
 * @author Ilya Krasavin
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {

    private Long id;
    private String title;
    private String text;
    private boolean readBy;

    public NotificationDto(Notification notification) {
        this.id = notification.getId();
        this.title = notification.getTitle();
        if (notification.getText().length() > 100) {
            this.setText(notification.getText().substring(0, 100) + "...");
        } else {
            this.text = notification.getText();
        }
        this.readBy = notification.isReadBy();
    }
}
