package info.loenwind.excessive.config;

import javax.annotation.Nonnull;

public enum Section {
  SERVER("server");

  @Nonnull
  public final String name;

  private Section(@Nonnull String name) {
    this.name = name;
  }

}
