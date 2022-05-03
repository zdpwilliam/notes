package com.william.pattern.composite.example;

/**
 * @author william.zhang
 */
public class Privilege {

	private Long id; 							// 用户权限id
	private String name; 						// 权限名称(type=1时，表示菜单名称；type=2时，表示操作名称)
	private Long parentId; 						// 所属菜单节点ID（type=1时，默认为0）

	public Privilege() {
	}

	public Privilege(Long id, String name, Long parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

    @Override
    public String toString() {
        return "Privilege{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}
