package pt.ist.ave.jzx.Instructions;

public class POPDE extends Instruction {

	public POPDE(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.de16(_cpu.pop16());
	}

	@Override
	public int incTstates() {
		return 10;
	}

}
