package com.aa.opengames.table.leave;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveTableRequest {
  private UUID tableId;
}
