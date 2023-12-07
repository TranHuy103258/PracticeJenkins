package swp.group2.learninghub.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swp.group2.learninghub.model.CardAttachment;
import swp.group2.learninghub.service.CardAttachmentService;


@RestController
@RequestMapping("/api/v1/card-attachments")
public class CardAttachmentController {
    private final CardAttachmentService cardAttachmentService;

    @Autowired
    public CardAttachmentController(CardAttachmentService cardAttachmentService) {
        this.cardAttachmentService = cardAttachmentService;
    }

    @PostMapping("/add")
    public ResponseEntity<CardAttachment> addAttachment(
            @RequestParam("cardId") int cardId,
            @RequestBody CardAttachment attachment) {
        try {
            attachment.setCardId(cardId);
            CardAttachment newAttachment = cardAttachmentService.addAttachment(attachment);
            return new ResponseEntity<>(newAttachment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<CardAttachment> updateAttachment(
            @RequestParam("cardId") int cardId,
            @RequestBody CardAttachment attachment) {
        try {
            CardAttachment existingAttachment = (CardAttachment) cardAttachmentService.getAttachmentsByCardId(cardId);
            if (existingAttachment != null) {
                attachment.setId(cardId);
                CardAttachment updatedAttachment = cardAttachmentService.updateAttachment(attachment);
                return new ResponseEntity<>(updatedAttachment, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAttachment(@RequestParam("cardId") int cardId) {
        try {
            CardAttachment existingAttachment = (CardAttachment) cardAttachmentService.getAttachmentsByCardId(cardId);
            if (existingAttachment != null) {
                cardAttachmentService.deleteAttachment(cardId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

