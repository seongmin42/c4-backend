package com.hanwha.backend.data.dto.request;

import com.hanwha.backend.data.IData;
import com.hanwha.backend.data.enums.ImageSize;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class PaintingRequestDto implements IData {
    @NonNull
    private String prompt;
    private int n = 1;
    private String size = ImageSize.LARGE.getSize();
}
