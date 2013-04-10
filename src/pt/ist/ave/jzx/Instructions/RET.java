package pt.ist.ave.jzx.Instructions;

public class RET extends Instruction {

	public RET(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_pc16(_cpu.pop16());
	}

	@Override
	public int incTstates() {
		return 10;
	}

}
