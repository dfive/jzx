package pt.ist.ave.jzx.Instructions;

public class OUT_MNA extends Instruction {

	public OUT_MNA(short opCode) {
		super(opCode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
	_cpu.getM_io().out((_cpu.getM_a8() << 8) | _cpu.getM_memory().read8(_cpu.inc16pc()), _cpu.getM_a8());

	}

	@Override
	public int incTstates() {
		return 11;
	}

}
